document.getElementById('submit-inquiry').addEventListener('click', () => {
  const token = document.getElementById("_csrf").content;
  const tokenHeader = document.getElementById("_csrf_header").content;
  const productNo = document.querySelector(".inquiry-tab").getAttribute("product-no");
  const title = document.getElementById("inquiry-title").value;
  const content = document.getElementById("inquiry-content").value;

  fetch('/api/inquires/product-inquiry', {
    headers : {
      'content-type' : 'application/json',
      [`${tokenHeader}`] : token
    },
    method : 'post',
    body : JSON.stringify({
      productNo : parseInt(productNo),
      title,
      inquiryContent : content
    })
  }).then(() => {
    window.location.reload();
  });
})
