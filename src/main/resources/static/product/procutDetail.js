document.querySelector(".review-tab").addEventListener("click", async (e) => {
  const productReview = document.querySelector('.product-review');
  if(productReview.children.length > 1){
    return;
  }
  const productNo = e.currentTarget.getAttribute("product-no");
  const response = await fetch(`/api/products/${productNo}/reviews`);
  const reviews = await response.json();

  reviews.content.forEach(review => {
    const reviewWrapper = document.createElement("div");
    const title = document.createElement("strong");
    const reviewInfoWrapper = document.createElement("div");
    const memberName = document.createElement("h7");
    const star = document.createElement("h7");
    const date = document.createElement("h7");
    const content = document.createElement("p");

    reviewWrapper.classList.add("row", "justify-content-between");
    reviewInfoWrapper.classList.add("row", "w-50", "justify-content-around");

    title.innerText = review.title;
    memberName.innerText = '작성자 : ' + review.writerNickname;
    star.innerText = '별점 : ' + review.starScore + ' 점';
    date.innerText = '작성날짜 : ' + review.registerDateTime;
    reviewInfoWrapper.append(memberName, star, date);
    content.innerText = review.content;
    reviewWrapper.append(title, reviewInfoWrapper);
    productReview.append(reviewWrapper, content);
  });
});

document.querySelector('.inquiry-tab').addEventListener('click', async (e) => {
  const productInquiry = document.querySelector('.product-inquiries');
  if(productInquiry.children.length > 2){
    return;
  }

  const productNo = e.currentTarget.getAttribute("product-no");
  const response = await fetch(`/api/inquires/products/${productNo}`);
  const inquiries = await response.json();

  inquiries.content.forEach(inquiry => {
    const inquiryWrapper = document.createElement("div");
    const title = document.createElement("strong");
    const inquiryInfoWrapper = document.createElement("div");
    const memberName = document.createElement("h7");
    const star = document.createElement("h7");
    const date = document.createElement("h7");
    const content = document.createElement("p");

    inquiryWrapper.classList.add("row", "justify-content-between");
    inquiryInfoWrapper.classList.add("row", "w-50", "justify-content-around");

    title.innerText = inquiry.title;
    memberName.innerText = '작성자 : ' + inquiry.memberNickname;
    star.innerText = '처리상태 : ' + inquiry.processStatus;
    date.innerText = '작성날짜 : ' + inquiry.registerDatetime;
    inquiryInfoWrapper.append(memberName, star, date);
    inquiryWrapper.append(title, inquiryInfoWrapper);
    productInquiry.append(inquiryWrapper, content);
  });
})
