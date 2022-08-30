import {loadAddressList} from "./addressListModule.js";
import {loadOrderProducts, orderRequestData} from "./orderProductsModule.js";

const addressListBtn = document.getElementById("addressListBtn");
let orderProductsContainer;

addressListBtn.addEventListener("click", () => {
    loadAddressList();
})

const init = () => {
    orderProductsContainer = document.getElementById("orderProductsContainer");
}

window.addEventListener("load", () => {
    init();
    loadOrderProducts();
    console.log(orderRequestData);
})