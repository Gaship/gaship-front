const reviewRetrieve = pageNo => async (e) => {
  const productReview = document.querySelector('.product-review');

  const productNo = e.currentTarget.getAttribute("product-no");
  const response = await fetch(`/api/products/${productNo}/reviews?page=${pageNo}`);
  const reviews = await response.json();

  reviews.content.forEach(review => {
    const reviewWrapper = document.createElement("div");
    const title = document.createElement("strong");
    const reviewInfoWrapper = document.createElement("div");
    const memberName = document.createElement("h7");
    const starLi = document.createElement("span");
    const date = document.createElement("h7");
    const content = document.createElement("p");

    reviewWrapper.classList.add("row", "justify-content-between");
    reviewInfoWrapper.classList.add("row", "w-50", "justify-content-around");

    title.innerText = review.title;
    memberName.innerText = '작성자 : ' + review.writerNickname;

    [...Array(5).keys()].forEach(i => {
      const star = document.createElement("i");

      if (i < review.starScore) {
        star.classList.add("fa", "fa-star", "g-pos-rel", "g-top-1", "g-mr-3");
      } else {
        star.classList.add("fa", "fa-star-o", "g-pos-rel", "g-top-1", "g-mr-3");
      }
      starLi.appendChild(star);
    })

    date.innerText = '작성날짜 : ' + review.registerDateTime;
    reviewInfoWrapper.append(memberName, starLi, date);
    content.innerText = review.content;
    reviewWrapper.append(title, reviewInfoWrapper);
    productReview.append(reviewWrapper, content);
  });
}

document.querySelector(".review-tab").addEventListener("click", (e) => {
  const productReview = document.querySelector('.product-review');
  if(productReview.children.length > 1){
    return;
  }

  reviewRetrieve(0)(e)
});

const inquiryRetrieve = pageNo => async (e) => {
  const productInquiry = document.querySelector('.product-inquiries');

  const productNo = e.currentTarget.getAttribute("product-no");
  const response = await fetch(`/api/inquires/products/${productNo}?page=${pageNo}`);
  const inquiries = await response.json();

  inquiries.content.forEach(inquiry => {
    const inquiryWrapper = document.createElement("div");
    const title = document.createElement("strong");
    const link = document.createElement("a");
    const inquiryInfoWrapper = document.createElement("div");
    const memberName = document.createElement("h7");
    const star = document.createElement("h7");
    const date = document.createElement("h7");

    inquiryWrapper.classList.add("row", "justify-content-between");
    inquiryInfoWrapper.classList.add("row", "w-50", "justify-content-around");

    link.classList.add('text-black-50')
    link.href = '/inquires/product-inquiries/'+ inquiry.inquiryNo;
    link.innerText = inquiry.title;
    title.append(link);

    memberName.innerText = '작성자 : ' + inquiry.memberNickname;
    star.innerText = '처리상태 : ' + inquiry.processStatus;
    date.innerText = '작성날짜 : ' + inquiry.registerDatetime;
    inquiryInfoWrapper.append(memberName, star, date);
    inquiryWrapper.append(title, inquiryInfoWrapper);
    productInquiry.append(inquiryWrapper);
  });
}

document.querySelector('.inquiry-tab').addEventListener('click', (e) => {
  const productInquiry = document.querySelector('.product-inquiries');
  if(productInquiry.children.length > 2){
    return;
  }
  inquiryRetrieve(0)(e);
} )


const addCart = function (e) {
  const token = document.getElementById("_csrf");
  const tokenHeader = document.getElementById("_csrf_header");
  const productNo = e.currentTarget.getAttribute("product-no");
  const quantityCount = document.querySelector(".quantity-input").value;

  fetch(`/carts/modify-quantity?productNo=${productNo}&productQuantity=${quantityCount}`, {
    headers : {
      'Content-Type':'application/json',
      [`${tokenHeader.content}`] : token.content
    },
    method : 'PUT',
  }).then(() => {
    toastr.success("장바구니에 담겼습니다.");
  });
};

document.querySelector(".insert-cat-btn").addEventListener('click',  addCart)

const readMore = fn => (e) => {
  const pageNo = e.target.getAttribute("data-page");
  e.target.setAttribute("data-page", parseInt(pageNo) + 1);

  fn(pageNo)(e);
}
document.querySelector(".more-read-btn").addEventListener('click', readMore(inquiryRetrieve))
document.querySelector(".more-review-read-btn").addEventListener('click', readMore(reviewRetrieve))


