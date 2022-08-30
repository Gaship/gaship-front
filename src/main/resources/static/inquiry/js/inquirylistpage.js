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

    await fetch(`/js/inquiries/${inquiryNo}?isProduct=${isProduct}`, request)
        .then(response => response.json())
        .then(data => {
            let redirectUri = data.redirectUri;
            location.href=redirectUri;
        });
}

