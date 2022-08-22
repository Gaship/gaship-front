const firstPage = 0;
const pageSize = 10;
let memberGradeTableBody;

// const pageHelper = {
//     pagingSize: 10,
//     page: 0,
//     lastPage: 0,
//     pageContainer:"",
//     lastPageContainer:"",
//     pageItems: [],
//     plusPage: (itemList) => {
//         pageHelper.page += 1;
//         pageHelper.reloadPage();
//         pageHelper.pageItems = itemList;
//     },
//     subPage: (itemList) => {
//         pageHelper.page -= 1;
//         pageHelper.reloadPage();
//         pageHelper.pageItems = itemList;
//     },
//     reloadPage: () => {
//         this.pageContainer.innerHtml = pageHelper.page + 1;
//     },
//     initPage: (itemList) => {
//         pageHelper.lastPageContainer.innerHtml = itemList.totalPages;
//         pageHelper.pageItems = itemList;
//         drawContent();
//     }
// }

// window.addEventListener("load", () => {
//     init();
//     console.log("1")
//     getMemberGradeData(firstPage, pageSize);
// })

// function init() {
//     memberGradeTableBody = document.getElementById("memberGradeTableBody");
// }

// async function getMemberGradeData(page, size) {
//     const request = {
//         method: get,
//         headers: {
//             "Content-Type": "application/json"
//         }
//     };
//     console.log("1")
//     const response = await fetch("/front/member-grades?page=" + page + "&size=" + size, request);
//     console.log(response);
// }

// function getMemberGradeDataPage(itemList) {
//     pageHelper.initPage(itemList);
// }

// function drawContent() {
//     pageHelper.pageItems.forEach(item => {
//         const trTemplate =
//             `
// <tr>
//     <td>${item.name}</td>
//     <td>${item.accumulateAmount}</td>
//     <td><button onclick="">수정</button></td>
//     <td><button onclick="">삭제</button></td>
// </tr>
//         `
//         memberGradeTableBody.insertAdjacentHTML("beforeend", trTemplate);
//     })
// }

