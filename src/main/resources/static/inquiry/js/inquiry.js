let token;
let tokenHeader;

window.addEventListener("load", () => {
    setElement();
})

function setElement() {
    token = document.getElementById("_csrf").getAttribute("content");
    tokenHeader = document.getElementById('_csrf_header').getAttribute("content");
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

    let path = inquiryNo;

    if (!isUser) {
        path += "/manager";
    }

    await fetch(`/js/inquiries/${path}?isProduct=${isProduct}`, request)
        .then(response => response.json())
        .then(data => {
            let redirectUri = data.redirectUri;
            location.href=redirectUri;
        });
}

function modifyInquiry() {
    const content = document.getElementById("answerContent");
    const modifyButton = document.getElementById("modifyButton");
    const submitButton = document.getElementById("submitButton");
    content.readOnly = false;
    modifyButton.style.display="none";
    submitButton.style.display="block";
}

async function submitInquiry(eventTarget) {
    const content = document.getElementById("answerContent");

    let inquiryNo = eventTarget.value;
    const request = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            [`${tokenHeader}`] : token
        },
        body: JSON.stringify({
            "answerContent" : content.value
        })
    };

    await fetch(`/js/inquiries/${inquiryNo}/inquiry-answer`, request)
        .then(() => {
            alert('문의답변 수정이 완료되었습니다.');
            location.reload();
        });
}

async function deleteInquiryAnswer(eventTarget) {

    let inquiryNo = eventTarget.value;

    const request = {
        method: "DELETE",
        headers: {
            [`${tokenHeader}`] : token
        }
    };

    await fetch(`/js/inquiries/${inquiryNo}/inquiry-answer`, request)
        .then(() => {
            alert('문의답변 삭제가 완료되었습니다.');
            location.reload();
        });
}
