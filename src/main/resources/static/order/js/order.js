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
            return response.json();
        })
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
        successUrl: 'https://gaship.shop/order/success?provider=TOSS',
        failUrl: 'https://gaship.shop/order/fail'
    })
}


doPaymentBtn.addEventListener('click', () => {
    orderRequestData.receiverName = document.getElementById("receiverNameInput").value;
    orderRequestData.receiverPhoneNo = document.getElementById("receiverPhoneNoInput").value;
    orderRequestData.receiverSubPhoneNo = document.getElementById("receiverSubPhoneNoInput").value;
    orderRequestData.deliveryRequest = document.getElementById("deliveryRequestInput").value;

    doOrder()
        .then(response => {
            if(response.status !== undefined) {
                throw new Error(`${response.message}`);
            } else {
                try {
                    doPayment(response)
                } catch (error) {
                    throw new Error('결제사의 사정으로 인해 결제가 불가능합니다.')
                }
                }
            })
        .catch(error => {
                window.alert("죄송합니다.\n" + error.message + "\n고객센터로 문의해주세요.")
                location.href = '/carts';
            })
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