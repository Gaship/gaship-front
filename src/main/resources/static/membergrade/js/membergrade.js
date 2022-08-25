const firstPage = 0;
const pageSize = 10;
let renewalPeriodContainer;
let memberGradeTableBody;
let token;
let tokenHeader;

const pageHelper = {
    pagingSize: 10,
    page: 0,
    lastPage: 0,
    pageContainer:"",
    lastPageContainer:"",
    pageItems: [],
    plusPage: (itemList) => {
        pageHelper.page += 1;
        pageHelper.reloadPage();
        pageHelper.pageItems = itemList;
    },
    subPage: (itemList) => {
        pageHelper.page -= 1;
        pageHelper.reloadPage();
        pageHelper.pageItems = itemList;
    },
    reloadPage: () => {
        this.pageContainer.innerHtml = pageHelper.page + 1;
    },
    initPage: (memberGradeList) => {
        pageHelper.lastPageContainer.innerHtml = memberGradeList.totalPages;
        pageHelper.pageItems = memberGradeList.content;
        drawMemberGradeContent();
    }
}

window.addEventListener("load", () => {
    init();
    drawRenewalPeriodSection();
    drawMemberGradeSection();
})

function drawRenewalPeriodSection() {
    getRenewalPeriodData()
        .then(drawRenewalPeriodContent)
        .then(setRenewalPeriodEvent);
}

function drawMemberGradeSection() {
    getMemberGradeData(firstPage, pageSize)
        .then(getMemberGradeDataPage)
        .then(setEvent);
}

function init() {
    memberGradeTableBody = document.getElementById("memberGradeTBody");
    token = document.getElementById("_csrf").textContent;
    tokenHeader = document.getElementById('_csrf_header').textContent;
}

async function getMemberGradeData(page, size) {
    const request = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    };

    return await fetch("/front/member-grades?page=" + page + "&size=" + size, request)
        .then(response => {
            return response.json();
        });
}

function getMemberGradeDataPage(memberGradeList) {
    pageHelper.initPage(memberGradeList);
}

function drawMemberGradeContent() {
    clearMemberGradeTBody();
    pageHelper.pageItems.forEach(item => {
        const trTemplate =
            `
<tr>
    <td id="${item.no}-name">${item.name}</td>
    <td id="${item.no}-accumulateAmount">${item.accumulateAmount}</td>
    <td>
        <button style="width: auto" name="updateBtn" value="${item.no}" class="btn btn-outline-primary">수정</button>
    </td>
    <td>
        <button style="width: auto" name="deleteBtn" value="${item.no}" class="btn btn-outline-primary">삭제</button>
    </td>
</tr>
        `
        memberGradeTableBody.insertAdjacentHTML("beforeend", trTemplate);
    })
}

function setEvent() {
    const deleteBtnList = document.getElementsByName("deleteBtn");
    const updateBtnList = document.getElementsByName("updateBtn");

    deleteBtnList.forEach(deleteBtn => {
        console.log(deleteBtn.value);
        deleteBtn.addEventListener("click",e => {
                deleteMemberGrade(deleteBtn.value)
                    .then(drawMemberGradeSection);
            }
        )
    })

    updateBtnList.forEach(updateBtn => {
        console.log(updateBtn.value);
        const memberGradeNo = updateBtn.value;
        const name = document.getElementById(memberGradeNo + "-name");
        const accumulateAmount = document.getElementById(memberGradeNo + "-accumulateAmount");

        const saveBtnTemplate = `
<button style="width: auto" value="${memberGradeNo}" onclick="updateMember(this)" class="btn btn-outline-primary">저장</button>
        `

        updateBtn.addEventListener("click", e => {
            name.innerHTML = modifyInputTemplate(name.innerText, memberGradeNo, '-name');
            accumulateAmount.innerHTML = modifyInputTemplate(accumulateAmount.innerText, memberGradeNo, '-accumulateAmount')
            updateBtn.outerHTML = saveBtnTemplate;
        })
    })
}

const modifyInputTemplate = (innerText, memberGradeNo, prefix) => {
    return `
<input style="width: auto" class="form-control" id="${memberGradeNo+prefix}Input" type="text" value="${innerText}">
        `
}


async function deleteMemberGrade(memberGradeNo) {
    const request = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            tokenHeader:token
        },
    };

    await fetch("/front/member-grades/" + memberGradeNo, request);
}

function updateMember(eventTarget) {
    const memberGradeNo = eventTarget.value;
    const nameInputValue = document.getElementById(memberGradeNo + '-nameInput').value;
    const accumulateAmountInputValue = document.getElementById(memberGradeNo + '-accumulateAmountInput').value;

    updateMemberGrade(memberGradeNo, nameInputValue, accumulateAmountInputValue)
        .then(drawMemberGradeSection);
}

function clearMemberGradeTBody() {
    memberGradeTableBody = document.getElementById("memberGradeTBody");
    memberGradeTableBody.innerHTML = "";
}

async function updateMemberGrade(memberGradeNo, name, accumulateAmount) {
    const request = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            tokenHeader:token
        },
        body: JSON.stringify({
            "name":name,
            "accumulateAmount":accumulateAmount
        })
    };

    await fetch("/front/member-grades/" + memberGradeNo, request);
}

async function getRenewalPeriodData() {
    const request = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    };

    return await fetch("/rest/renewal-period", request)
        .then(response => {
            return response.json();
        });
}

function drawRenewalPeriodContent(renewalPeriod) {
    console.log(renewalPeriod);
    renewalPeriodContainer = document.getElementById("renewalPeriodContainer");
    renewalPeriodContainer.value = `${renewalPeriod.explanation}`;
}

function setRenewalPeriodEvent() {
    const renewalPeriodModifyBtn = document.getElementById("renewalPeriodModifyBtn");
    renewalPeriodModifyBtn.addEventListener("click", t => {
        const renewalPeriodValue = renewalPeriodContainer.value;
        renewalPeriodContainer.outerHTML = `
        <input style="width: 50px" class="form-control" id="renewalModifyInput" type="text" value="${renewalPeriodValue}">
        `
    })
}