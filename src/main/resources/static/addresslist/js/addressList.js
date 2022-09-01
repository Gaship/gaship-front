const createAddressBtn = document.getElementById("createAddressBtn");
const searchAddressBtn = document.getElementById("searchAddressBtn");
const modalCancelBtn = document.getElementById("cancelBtn");
const modalSaveBtn = document.getElementById("saveBtn");
const token = document.querySelector('meta[name="_csrf"]').content;

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

createAddressBtn.addEventListener('click', () => {
    document.querySelector(".addressAddFormModalWrap").style.display = 'block';
    document.querySelector(".modalBackground").style.display = 'block';
})

searchAddressBtn.addEventListener('click', () => {
    new daum.Postcode({
        oncomplete: function(data) {
            addressAddRequestData.init(data);
        }
    }).open();
})

modalCancelBtn.addEventListener('click', () => {
    document.querySelector(".addressAddFormModalWrap").style.display = 'none';
    document.querySelector(".modalBackground").style.display = 'none';
})

modalSaveBtn.addEventListener('click', () => {
    addressAddRequestData.addressDetail = document.getElementById("addressDetail").value;

    if(addressAddRequestData.checkBlank()) {
        window.alert("모두 입력해주세요.");
    } else {
        addAddress()
            .then(() => {
                location.href = '/member/address-list'
            })
    }
})
