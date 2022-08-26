import pageHelper from "./pageModule.js";

const addressListPageHelper = pageHelper;
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

async function drawAddressListContainer() {
    addressListContainer.innerHTML = `
<div class="card">
    <div class="card-header">
        <h3 class="card-title">배송지 목록</h3>
        <div class="card-tools">
            <div class="input-group input-group-sm" style="width: 150px;">
                <input type="text" name="table_search" class="form-control float-right" placeholder="배송지 이름을 입력하세요.">
                <div class="input-group-append">
                    <button type="submit" class="btn btn-default">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>
        </div>
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
    <td></td>
</tr>
            `;
        addressListContent.insertAdjacentHTML("beforeend", trTemplate);
    })

}

function init() {
    addressListContainer = document.getElementById("addressListContainer");
}

const loadAddressList = () => {
    init();
    getAddressListData()
        .then(addressListPageHelper.initPage)
        .then(drawAddressListContainer)
        .then(drawAddressListContent);
}

export {addressListPageHelper, loadAddressList}