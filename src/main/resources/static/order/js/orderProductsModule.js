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
    this.amount = orderProductData.amount;
    this.productName = orderProductData.productName;
    this.couponNo = null;
    this.couponAmount = 0;
    this.hopeDate = "";
}

const orderRequestData = {
    addressListNo: "",
    memberNo: "",
    orderProducts: [],
    receiverName: "",
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
        productTarget[0].couponNo = couponNo;
        productTarget[0].couponAmount = memberCoupons.getDiscountAmount(productTarget[0].amount, couponNo);
        const orderProductArrayIndex = orderRequestData.orderProducts.indexOf(productTarget[0]);
        orderRequestData.orderProducts[orderProductArrayIndex] = productTarget[0];
        orderRequestData.setTotalDiscountAmount();
        orderRequestData.setTotalAmount();
        setOrderAmountInfo();
        drawOrderProductsContent();

        memberCoupons.selectCoupon(couponNo);
    },
    setTotalDiscountAmount: () => {
        discountTotalAmount = orderRequestData.orderProducts
            .reduce((accumulate, orderProduct) => accumulate + orderProduct.couponAmount, 0);
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

function drawOrderProductsContent() {
    orderProductsContainer = document.getElementById("orderProductsContainer");
    orderProductsContainer.innerHTML = "";

    orderRequestData.orderProducts.forEach(orderProduct => {
        const orderProductContentTemplate = `
        <tr>
            <td style="padding: 10px" class="shoping__cart__item">
                <img style="padding: 20px; width: 30%; height: auto" src="${orderProduct.imgPath}" alt="">
                <h5>${orderProduct.productName}</h5><br/>
            </td>
            <td style="padding: 10px" class="shoping__cart__price">
                ${orderProduct.amount}
            </td>
            <td style="padding: 10px" class="shoping__cart__price">
                ${orderProduct.couponAmount}
            </td>
            <td style="padding: 10px" class="shoping__cart__total">
                ${orderProduct.amount - orderProduct.couponAmount}
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
            if(selectCouponNo === ""){

            } else {
                if(memberCoupons.checkSelectedCoupon(selectCouponNo)) {
                    window.alert("이미 선택된 쿠폰입니다.")
                    e.target.options(0).selected = true;
                } else {
                    orderRequestData.setCoupon(selectCouponNo, productIndex);
                }
            }
        })
    })
}

function setOrderAmountInfo() {
    document.getElementById("productTotalAmount").innerText = orderProductsTotalAmount;
    document.getElementById("discountTotalAmount").innerText = discountTotalAmount;
    document.getElementById("paymentTotalAmount").innerText = orderRequestData.totalAmount;
}

const loadOrderProducts = () => {
    loadMemberCoupons();
    getOrderProducts()
        .then(setOrderRequestData)
        .then(setCouponEvent);
}

export {loadOrderProducts, orderRequestData}