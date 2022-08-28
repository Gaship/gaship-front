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
    orderProductsTotalAmount =
        orderProductList.reduce((totalAmount, orderProduct) =>
            totalAmount + (orderProduct.amount * orderProduct.orderQuantity), 0);

    totalProductQuantity = orderProductList
        .reduce((totalQuantity, orderProduct) =>
            totalQuantity + orderProduct.orderQuantity);
    orderName = orderProductList[1].productName + " 외 " + totalProductQuantity + "건";

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
                <h5>${orderProduct.productName}</h5>
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
                style="padding: 10px" class="site-btn">쿠폰 선택</button>
            </td>
        </tr>
        `
        orderProductsContainer.insertAdjacentHTML("beforeend", orderProductContentTemplate);
    })
}

const loadOrderProducts = () => {
    getOrderProducts()
        .then(setOrderRequestData);
}

export {loadOrderProducts, orderRequestData}