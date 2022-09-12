import {loadMemberCoupons, memberCoupons} from "./couponListModule.js";

let orderProductsContainer;

let orderName;
let orderProductsTotalAmount = 0;
let discountTotalAmount = 0;
let totalProductQuantity = 0;

function OrderProduct(index, orderProductData) {
    this.index = index;
    this.imgPath = orderProductData.filePaths;
    this.productNo = orderProductData.productNo;
    this.productAmount = orderProductData.amount;
    this.productName = orderProductData.productName;
    this.couponNo = null;
    this.couponAmount = 0;
    this.amount = orderProductData.amount;
    this.hopeDate = "";
}

const orderRequestData = {
    addressListNo: "",
    orderProducts: [],
    receiverName: "",
    receiverPhoneNo: "",
    receiverSubPhoneNo: "",
    deliveryRequest: "",
    totalAmount: 0,
    init: (orderProductList) => {
        orderProductList.forEach(orderProduct => {
            for(let i = 0;i < orderProduct.orderQuantity; i++) {
                orderRequestData.orderProducts
                    .push(new OrderProduct(orderProduct.productNo + "-" + i, orderProduct));
            }
        })
        orderRequestData.setTotalAmount();
    },
    setTotalAmount: () => {
        orderRequestData.totalAmount = orderProductsTotalAmount - discountTotalAmount;
    },
    setCoupon: (couponNo, productIndex) => {
        const productTarget = orderRequestData.orderProducts
            .filter(orderProduct => orderProduct.index === productIndex);

        if(productTarget[0].couponNo !== null) {
            memberCoupons.unselectCoupon(productTarget[0].couponNo);

            productTarget[0].couponNo = null;
            productTarget[0].couponAmount = 0;
            productTarget[0].amount = productTarget[0].productAmount;
        }

        if(couponNo === "") {
            productTarget[0].couponNo = null;
            productTarget[0].couponAmount = 0;
            productTarget[0].amount = productTarget[0].productAmount;
            orderRequestData.reloadData(productTarget[0]);
        } else {
            productTarget[0].couponNo = couponNo;
            productTarget[0].couponAmount = memberCoupons.getDiscountAmount(productTarget[0].amount, couponNo);
            productTarget[0].amount = productTarget[0].productAmount-productTarget[0].couponAmount;
            orderRequestData.reloadData(productTarget[0]);

            memberCoupons.selectCoupon(couponNo);
        }
    },
    reloadData:(productTarget) => {
        const orderProductArrayIndex = orderRequestData.orderProducts.indexOf(productTarget);
        orderRequestData.orderProducts[orderProductArrayIndex] = productTarget;
        orderRequestData.setTotalDiscountAmount();
        orderRequestData.setTotalAmount();
        setOrderAmountInfo();
        drawOrderProductsContent();
    },
    setTotalDiscountAmount: () => {
        discountTotalAmount = orderRequestData.orderProducts
            .reduce((accumulate, orderProduct) => accumulate + orderProduct.couponAmount, 0);
    },
    setAddress: (addressListNo) => {
        orderRequestData.addressListNo = addressListNo;
    }
}

async function getOrderProducts() {
    const request = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    };

    return await fetch("/rest/order/products", request)
        .then(response => {
            return response.json();
        });
}

function setOrderRequestData(orderProductList) {
    if(orderProductList.length === 0){
        window.alert("장바구니가 비었습니다.");
        location.href = '/carts';
    }

    orderProductsTotalAmount =
        orderProductList.reduce((totalAmount, orderProduct) =>
            totalAmount + (orderProduct.amount * orderProduct.orderQuantity), 0);

    totalProductQuantity = orderProductList
        .reduce((totalQuantity, orderProduct) =>
            totalQuantity + orderProduct.orderQuantity);

    orderName = orderProductList[0].productName + " 외 " + totalProductQuantity + "건";
    orderRequestData.init(orderProductList);

    drawOrderProductsContent();
}

const parseKoreaCurrency = (amount) => new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' })
  .format(amount);

function drawOrderProductsContent() {
    orderProductsContainer = document.getElementById("orderProductsContainer");
    orderProductsContainer.innerHTML = "";

    orderRequestData.orderProducts.forEach(orderProduct => {
      const amount = parseKoreaCurrency(orderProduct.amount);
      const couponAmount = parseKoreaCurrency(orderProduct.couponAmount);
      const productAmount = parseKoreaCurrency(orderProduct.productAmount);

        const orderProductContentTemplate = `
        <tr>
            <td style="padding: 10px" class="shoping__cart__item">
                <img style="padding: 20px; width: 30%; height: auto" src="${orderProduct.imgPath}" alt="">
                <h5>${orderProduct.productName}</h5><br/>
            </td>
            <td style="padding: 10px" class="shoping__cart__price">
                ${productAmount}
            </td>
            <td style="padding: 10px" class="shoping__cart__price">
                ${couponAmount}
            </td>
            <td style="padding: 10px" class="shoping__cart__total">
                ${amount}
            </td>
            <td style="padding: 10px">
                <button name="selectCouponBtn" value="${orderProduct.index}" 
                style="padding: 10px" class="site-btn">쿠폰 선택</button><br/>
                <div style="margin: 10px; display: none" id="${orderProduct.index}-couponSelectBoxContainer">
                <select id="${orderProduct.index}-couponSelectBox" name="couponSelectBox">
                    <option value="">쿠폰선택</option>
                </select>
                </div>
            </td>
        </tr>
        `;

        orderProductsContainer.insertAdjacentHTML("beforeend", orderProductContentTemplate);
    })

    setOrderAmountInfo();
    setCouponEvent();
}

function clearCouponSelectBox(targetValue) {
    const couponSelectBox = document.getElementById(targetValue + "-couponSelectBox");
    couponSelectBox.innerHTML = `<option value="">쿠폰선택</option>`;
}

function setCouponEvent() {
    const selectCouponBtnList = document.getElementsByName("selectCouponBtn");

    selectCouponBtnList.forEach(btn => {
        btn.addEventListener('click', e => {
            const targetValue = e.target.value;
            const couponSelectBox = document.getElementById(targetValue + "-couponSelectBox");
            let optionTemplate;
            couponSelectBox.addEventListener('click', () => {
                clearCouponSelectBox(targetValue);
                couponSelectBox.insertAdjacentHTML("beforeend", `
                    <option value="">선택안함</option>
                `)
                memberCoupons.getUnselectedCoupon().forEach(coupon => {
                    if(coupon.used === true) {
                        optionTemplate = `
                <option value="${coupon.couponNo}" disabled>${coupon.couponName} (사용만료일 : ${coupon.expirationDatetime})</option>
                `
                    }
                    else {
                        optionTemplate = `
                <option value="${coupon.couponNo}">${coupon.couponName} (사용만료일 : ${coupon.expirationDatetime})</option>
                `
                    }

                    couponSelectBox.insertAdjacentHTML("beforeend", optionTemplate);
                })
            })

            document.getElementById(targetValue+"-couponSelectBoxContainer").style.display = 'block';
        })
    })

    const selectCouponBoxList = document.getElementsByName("couponSelectBox");

    selectCouponBoxList.forEach(selectCouponBox => {
        selectCouponBox.addEventListener('change', e => {
            const selectCouponNo = e.target.value;
            const productIndex = e.target.id.split("-couponSelectBox")[0];

            orderRequestData.setCoupon(selectCouponNo, productIndex);
        })
    })
}

function setOrderAmountInfo() {
    document.getElementById("productTotalAmount").innerText = parseKoreaCurrency(orderProductsTotalAmount);
    document.getElementById("discountTotalAmount").innerText = parseKoreaCurrency(discountTotalAmount);
    document.getElementById("paymentTotalAmount").innerText = parseKoreaCurrency(orderRequestData.totalAmount);
}

const loadOrderProducts = () => {
    loadMemberCoupons();
    getOrderProducts()
        .then(setOrderRequestData);
}

export {loadOrderProducts, orderRequestData}
