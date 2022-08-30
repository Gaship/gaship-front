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

    await fetch("/rest/order", request)
}


doPaymentBtn.addEventListener('click', () => {
    orderRequestData.receiverName = document.getElementById("receiverNameInput").value;
    orderRequestData.receiverPhoneNo = document.getElementById("receiverPhoneNoInput").value;
    orderRequestData.receiverSubPhoneNo = document.getElementById("receiverSubPhoneNoInput").value;
    orderRequestData.deliveryRequest = document.getElementById("deliveryRequestInput").value;

    window.alert(JSON.stringify(orderRequestData));
    doOrder()
        .then();
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