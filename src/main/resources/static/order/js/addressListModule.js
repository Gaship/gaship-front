import {pageHelper} from "./pageModule.js";
import {orderRequestData} from "./order.js";

const addressListPageHelper = pageHelper;
let token;
let addressListContainer;

async function getAddressListData() {
    const request = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    };

    return await fetch("/rest/members/address-list?page=0&size=5", request)
        .then(response => {
            return response.json();
        });
}

function drawAddressListContainer() {
    addressListContainer.innerHTML = `
<div class="card">
    <div style="display: flex" class="card-header">
        <h4 class="card-title">배송지 목록</h4>
        <span>
            <button id="createAddressBtn" style="margin-left: 10px;padding: 10px" class="site-btn">신규 배송지 등록</button>
        </span>
    </div>

    <div class="card-body table-responsive p-0" style="height: 300px;">
        <table class="table table-head-fixed text-nowrap">
            <thead>
            <tr>
                <th>#</th>
                <th>배송지 이름</th>
                <th>주소</th>
                <th>상세주소</th>
                <th>우편번호</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="addressListContent">

            </tbody>
        </table>
    </div>
</div>
    `
}

function drawAddressListContent() {
    const addressListContent = document.getElementById("addressListContent");
    addressListContent.innerHTML = "";
    addressListPageHelper.pageItems.forEach(item => {
        const trTemplate =
            `
<tr>
    <td>${item.addressListNo}</td>
    <td>${item.addressName}</td>
    <td>${item.address}</td>
    <td>${item.addressDetail}</td>
    <td>
        ${item.zipCode}
    </td>
    <td>
    <button name="selectAddress" value="${item.addressListNo}" 
                style="padding: 10px" class="site-btn">선택
    </button>
    </td>
</tr>
            `;
        addressListContent.insertAdjacentHTML("beforeend", trTemplate);
    })

    setSelectAddressBtnEvent();
}

function setSelectAddressBtnEvent() {
    const selectAddressBtnList = document.getElementsByName("selectAddress");
    selectAddressBtnList.forEach(btn => {
        btn.addEventListener('click', e => {
            const addressListNo = e.target.value;

            const selectAddress = addressListPageHelper.pageItems
                .filter(addressInfo => addressInfo.addressListNo == addressListNo);

            document.getElementById("sigunguInput").value = selectAddress[0].addressName;
            document.getElementById("roadAddressInput").value = selectAddress[0].address;
            document.getElementById("addressDetailsInput").value = selectAddress[0].addressDetail;
            document.getElementById("zonecodeInput").value = selectAddress[0].zipCode;


            orderRequestData.setAddress(addressListNo);

            document.getElementById("addressListContainer").style.display = 'none';
        })
    })
}

function init() {
    token = document.querySelector('meta[name="_csrf"]').content;
    addressListContainer = document.getElementById("addressListContainer");
    addressListContainer.innerHTML = "";
}

function setCreateAddressEvent() {
    const addressAddFormModalWrap = document.querySelector(".addressAddFormModalWrap");

    const createAddressBtn = document.getElementById("createAddressBtn");
    createAddressBtn.addEventListener("click", () => {
        document.querySelector(".addressAddFormModalWrap").style.display = 'block';
        document.querySelector(".modalBackground").style.display = 'block';
        addressAddFormModalWrap.innerHTML = `
    <div style="margin: 20px" id="addAddressFormModal" class="checkout__input">
        <p>배송지<span>*</span>
            <span style="margin: 10px">
                <button id="searchAddressBtn" style="padding: 10px" class="site-btn">주소 검색</button>
            </span>
        </p>
        <div class="checkout__input">
            <p>우편번호<span>*</span></p>
            <input id="zoneCode" readonly="readonly" type="text" value="">
        </div>
        <div class="checkout__input">
            <p>시군구<span>*</span></p>
            <input id="sigungu" readonly="readonly" type="text" value="">
        </div>
        <div class="checkout__input">
            <p>상세주소<span>*</span></p>
            <input id="roadAddress" readonly="readonly" type="text" value="" class="checkout__input__add">
            <input id="addressDetail" type="text" placeholder="상세 주소를 작성해주세요.">
        </div>
        <button id="saveBtn" style="padding: 10px" class="site-btn">등록</button>
        <span>
            <button id="cancelBtn" style="padding: 10px" class="site-btn">취소</button>
        </span>
    </div>
    `
        setAddressAddFormEvent();
    })
}

const addressAddRequestData = {
    zonecode: "",
    sigungu: "",
    roadAddress: "",
    addressDetail: "",
    init: (data) => {
        addressAddRequestData.roadAddress = data.roadAddress;
        addressAddRequestData.sigungu = data.sigungu;
        addressAddRequestData.zonecode = data.zonecode;
        addressAddRequestData.updateForm();
    },
    updateForm: () => {
        document.getElementById("zoneCode").value = addressAddRequestData.zonecode;
        document.getElementById("sigungu").value = addressAddRequestData.sigungu;
        document.getElementById("roadAddress").value = addressAddRequestData.roadAddress;
    },
    checkBlank: () => {
        if(addressAddRequestData.zonecode==""||
            addressAddRequestData.sigungu==""||
            addressAddRequestData.roadAddress==""||
            addressAddRequestData.addressDetail=="") {
            return true;
        }
        return false;
    }
}

function setAddressAddFormEvent() {
    const searchAddressBtn = document.getElementById("searchAddressBtn");
    const modalCancelBtn = document.getElementById("cancelBtn");
    const modalSaveBtn = document.getElementById("saveBtn");

    searchAddressBtn.addEventListener("click", () => {
        new daum.Postcode({
            oncomplete: function(data) {
                addressAddRequestData.init(data);
            }
        }).open();
    })

    modalCancelBtn.addEventListener("click", () => {
        document.querySelector(".addressAddFormModalWrap").style.display = 'none';
        document.querySelector(".modalBackground").style.display = 'none';
        addressAddFormModalWrap.innerHTML = "";
    })

    modalSaveBtn.addEventListener("click", () => {
        addressAddRequestData.addressDetail = document.getElementById("addressDetail").value;

        if(addressAddRequestData.checkBlank()) {
            window.alert("모두 입력해주세요.");
        } else {
            addAddress()
                .then(() => {
                    document.querySelector(".addressAddFormModalWrap").style.display = 'none';
                    document.querySelector(".modalBackground").style.display = 'none';
                    addressAddFormModalWrap.innerHTML = "";
                })
                .then(loadAddressList);
        }
    })
}

async function addAddress() {
    const request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(addressAddRequestData)
    };

    await fetch("/rest/members/address-list", request);
}

const loadAddressList = () => {
    init();
    getAddressListData()
        .then(addressListPageHelper.initPage)
        .then(drawAddressListContainer)
        .then(setCreateAddressEvent)
        .then(drawAddressListContent);
}

export {addressListPageHelper, loadAddressList}