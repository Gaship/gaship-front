const currentLocation = document.querySelector(".current-location");

if(location.href.match(/exit/)) {
  const exitLiTag = document.querySelector(".link-exit");
  currentLocation.innerHTML = exitLiTag.firstChild.textContent;
} else if(location.href.match(/order-product/)){
  const ordersLiTag = document.querySelector(".link-orders");
  currentLocation.innerHTML = ordersLiTag.firstChild.textContent;
} else if(location.href.match(/addresses/)){
  const addressLiTag = document.querySelector(".link-address");
  currentLocation.innerHTML = addressLiTag.firstChild.textContent;
} else if(location.href.match(/member-tag/)){
  const tagLiTag = document.querySelector(".link-tag");
  currentLocation.innerHTML = tagLiTag.firstChild.textContent;
} else if(location.href.match(/reviews/)){
  const reviewLiTag = document.querySelector(".link-review");
  currentLocation.innerHTML = reviewLiTag.firstChild.textContent;
} else if(location.href.match(/customer-inquiries/)){
  const customerLiTag = document.querySelector(".link-customer-inquiry");
  currentLocation.innerHTML = customerLiTag.firstChild.textContent;
} else if(location.href.match(/product-inquiries/)){
  const productLiTag = document.querySelector(".link-product-inquiry");
  currentLocation.innerHTML = productLiTag.firstChild.textContent;
} else {
  const infoLiTag = document.querySelector(".link-info");
  currentLocation.innerHTML = infoLiTag.firstChild.textContent;
}
