import {addressListPageHelper, loadAddressList} from "./addressListModule.js";

const addressListBtn = document.getElementById("addressListBtn");
let orderProductsContainer;


window.addEventListener("load", () => {
    init();
})

const init = () => {
    orderProductsContainer = document.getElementById("orderProductsContainer");
}

addressListBtn.addEventListener("click", () => {
    loadAddressList();
    console.log(addressListPageHelper.pageItems);
})