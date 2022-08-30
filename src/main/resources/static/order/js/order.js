import {loadAddressList} from "./addressListModule.js";
import {loadOrderProducts, orderRequestData} from "./orderProductsModule.js";

const addressListBtn = document.getElementById("addressListBtn");
const doPaymentBtn = document.getElementById("doPaymentBtn");
let orderProductsContainer;

addressListBtn.addEventListener("click", () => {
    document.getElementById("addressListContainer").style.display = 'block';
    loadAddressList();
})

doPaymentBtn.addEventListener('click', () => {
    orderRequestData.receiverName = document.getElementById("receiverNameInput").value;
    orderRequestData.receiverPhoneNo = document.getElementById("receiverPhoneNoInput").value;
    orderRequestData.receiverSubPhoneNo = document.getElementById("receiverSubPhoneNoInput").value;
    orderRequestData.deliveryRequest = document.getElementById("deliveryRequestInput").value;

    window.alert(JSON.stringify(orderRequestData));
})

const init = () => {
    orderProductsContainer = document.getElementById("orderProductsContainer");
}

window.addEventListener("load", () => {
    init();
    loadOrderProducts();
})


export {orderRequestData}