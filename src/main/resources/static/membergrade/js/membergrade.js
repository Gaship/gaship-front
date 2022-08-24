const firstPage = 0;
const pageSize = 10;
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
        drawContent();
    }
}

window.addEventListener("load", () => {
    init();
    drawPage();
})

function drawPage() {
    getMemberGradeData(firstPage, pageSize)
        .then(getMemberGradeDataPage)
        .then(setEvent);
}

function init() {
    memberGradeTableBody = document.getElementById("memberGradeTBody");
    token = document.querySelector('meta[name="_csrf"]').textContent;
    tokenHeader = document.querySelector('meta[name="_csrf_header"]').textContent;
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

async function drawContent() {
    clearMemberGradeTBody();
    pageHelper.pageItems.forEach(item => {
        const trTemplate =
            `
<tr>
    <td id="${item.no}-name">${item.name}</td>
    <td id="${item.no}-accumulateAmount">${item.accumulateAmount}</td>
    <td><button name="updateBtn" value="${item.no}">수정</button></td>
    <td><button name="deleteBtn" value="${item.no}">삭제</button></td>
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
                    .then(drawPage);
            }
        )
    })

    updateBtnList.forEach(updateBtn => {
        console.log(updateBtn.value);
        const memberGradeNo = updateBtn.value;
        const name = document.getElementById(memberGradeNo + "-name");
        const accumulateAmount = document.getElementById(memberGradeNo + "-accumulateAmount");

        const saveBtnTemplate = `
        <button value="${memberGradeNo}" onclick="updateMember(this)">저장</button>
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
        <input id="${memberGradeNo+prefix}Input" type="text" value="${innerText}"/>
        `
}


async function deleteMemberGrade(memberGradeNo) {
    const request = {
        credentials: "include",
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
        .then(drawPage);
}

function clearMemberGradeTBody() {
    memberGradeTableBody.innerHTML = "";
}

async function updateMemberGrade(memberGradeNo, name, accumulateAmount) {
    const request = {
        credentials: "include",
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

