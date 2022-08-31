import {loadAddressList} from "./addressListModule.js";
import {loadOrderProducts, orderRequestData} from "./orderProductsModule.js";

const addressListBtn = document.getElementById("addressListBtn");
const doPaymentBtn = document.getElementById("doPaymentBtn");
let orderProductsContainer;
let token;

addressListBtn.addEventListener("click", () => {
    document.getElementById("addressListContainer").style.display = 'block';
    loadAddressList();
})

async function doOrder() {
    const request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN":token
        },
        body: JSON.stringify(orderRequestData)
    };

    return await fetch("/rest/order", request)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            window.alert("죄송합니다. 현재 재고가 부족합니다.")
            location.href = '/carts';
        });
}

function doPayment(orderRegisterResponseData) {
    const clientKey = "test_ck_oeqRGgYO1r5KG7pq5bp3QnN2Eyaz"
    const tossPayments = TossPayments(clientKey)

    const inputList = document.getElementsByTagName('input');
    for (let i = 0; i < inputList.length; i++) {
        inputList[i].setAttribute('readonly', true);
    }

    tossPayments.requestPayment('카드',{
        amount: orderRegisterResponseData.amount,
        orderId: "gaship-"+orderRegisterResponseData.orderId,
        orderName: orderRegisterResponseData.orderName,
        customerName: orderRegisterResponseData.customerName,
        successUrl: 'http://localhost:8080/order/success?provider=TOSS',
        failUrl: 'http://localhost:8080/rest/order/success'
    })
}


doPaymentBtn.addEventListener('click', () => {
    orderRequestData.receiverName = document.getElementById("receiverNameInput").value;
    orderRequestData.receiverPhoneNo = document.getElementById("receiverPhoneNoInput").value;
    orderRequestData.receiverSubPhoneNo = document.getElementById("receiverSubPhoneNoInput").value;
    orderRequestData.deliveryRequest = document.getElementById("deliveryRequestInput").value;

    doOrder()
        .then(doPayment);
})

const init = () => {
    orderProductsContainer = document.getElementById("orderProductsContainer");
    token = document.querySelector('meta[name="_csrf"]').content;
}

window.addEventListener("load", () => {
    init();
    loadOrderProducts();
})


export {orderRequestData}