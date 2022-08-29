const firstPage = 0;
const pageSize = 10;

let inquiryListTBodyElement;
let token;
let tokenHeader;

window.addEventListener("load", () => {
    setElement();
    drawProductInquiryListPage();
})

function setElement() {
    inquiryListTBodyElement = document.getElementById("inquiryListTBody");
    token = document.getElementById("_csrf").getAttribute("content");
    tokenHeader = document.getElementById('_csrf_header').getAttribute("content");
}

function drawProductInquiryListPage() {
    getProductInquiryPageResponse(firstPage, pageSize)
        .then(pageHelper.initPage);
}

async function getProductInquiryPageResponse(firstPage, pageSize) {
    const request = {
        method : "GET"
    };

    return await fetch(`/js/inquiries/member-self/${whereUri}-inquiries?page=${firstPage}&size=${pageSize}`, request)
        .then(response => {
            return response.json();
        });
}

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
    initPage: (pageResponse) => {
        pageHelper.lastPageContainer.innerHtml = pageResponse.totalPages;
        pageHelper.pageItems = pageResponse.content;
        drawProductInquiryListTBody();
    }
}


function drawProductInquiryListTBody() {
    inquiryListTBodyElement.innerHtml = "";

    pageHelper.pageItems.forEach(item => {
        let tbody =
            `
                <tr>
                    <td>${item.inquiryNo}</td>
                    <td id = "${item.inquiryNo}-title"><a href="/inquiries/${whereUri}-inquiries/${item.inquiryNo}">${item.title}</a></td>
                    <td id = "${item.inquiryNo}-processStatus">${item.processStatus}</td>
                    <td id = "${item.inquiryNo}-memberNickname">${item.memberNickname}</td>
                    <td id = "${item.inquiryNo}-registerDatetime">${item.registerDatetime}</td>
            `;

        if (item.memberNo == memberNo) {
            tbody += `<td><button style="width: auto; margin-left: 10px" class="btn btn-outline-primary" value="${item.inquiryNo}" onclick="deleteInquiry(this)">삭제</button></td>`;
        }

        tbody += `</tr>`;
        inquiryListTBodyElement.insertAdjacentHTML("beforeend", tbody);
    })
}

async function deleteInquiry(eventTarget) {
    const request = {
        method: "DELETE",
        headers: {
            [`${tokenHeader}`] : token
        }
    };

    let inquiryNo = eventTarget.value;
    let isProduct = false;
    if (whereUri == "product") {
        isProduct = true;
    }

    await fetch(`/js/inquiries/${inquiryNo}?isProduct=${isProduct}`, request)
        .then(response => response.json())
        .then(data => {
            let redirectUri = data.redirectUri;
            location.href=redirectUri;
        });
}

