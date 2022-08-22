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
    getMemberGradeData(firstPage, pageSize)
        .then(getMemberGradeDataPage)
        .then(setEvent);
})

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
                    .then();
            }
        )
    })

    updateBtnList.forEach(updateBtn => {
        console.log(updateBtn.value);
        const memberGradeNo = updateBtn.value;
        const name = document.getElementById(memberGradeNo + "-name");
        const accumulateAmount = document.getElementById(memberGradeNo + "-accumulateAmount");
        const nameInputTemplate = `
        <input type="text" value="${name.innerText}"/>
        `
        updateBtn.addEventListener("click", e => {
            name.innerHTML=nameInputTemplate;
        })
    })
}
const inputTemplate = (innerText) => {
    return `
        <input type="text" value="${innerText}"/>
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

