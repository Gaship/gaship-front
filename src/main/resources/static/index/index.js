const createProductWrapper = () => {
  const productWrapper = document.createElement("div");
  productWrapper.classList.add("col-lg-3", "col-md-4", "col-sm-6", "mix");

  return productWrapper;
}

const createProductImageWrapper = () => {
  const productImgWrapper = document.createElement("div");
  productImgWrapper.classList.add("featured__item");

  return productImgWrapper;
}

const createAmountLabel = (productNo, productName, amount) => {
  const productItemInfo = document.createElement("div");
  productItemInfo.classList.add("featured__item__text");
  productItemInfo.innerHTML =
    `<h6><a href="/products/${productNo}">${productName}</a></h6><h5>₩${amount}</h5>`

  return productItemInfo;
}

const createProductImageDiv = (imageUrl, productNo, productName) => {
  const productImg = document.createElement("div");
  productImg.classList.add("featured__item__pic", "set-bg");
  productImg["data-setbg"] = imageUrl;
  productImg.style.backgroundImage = `url("${imageUrl}")`;

  const basketButtonWrapper = document.createElement("ul");
  basketButtonWrapper.classList.add("featured__item__pic__hover");
  basketButtonWrapper.innerHTML =
    `<li><a onclick="addCart(${productNo})"><i class="fa fa-shopping-cart"></i></a></li>`;
  productImg.appendChild(basketButtonWrapper);
  return productImg;
}


window.addEventListener("load", async () => {
  const productSliderWrapper = document.querySelector(".product-wrapper");
  const res = await fetch("/api/products?page=0&size=12");
  const pageSlideProducts = await res.json();
  pageSlideProducts.content
    .map(product => {
      const productWrapper = createProductWrapper();
      const productImageWrapper = createProductImageWrapper();
      const productImageDiv = createProductImageDiv(((product.filePaths && product.filePaths[0]) || "img/no-photo.png"),product.productNo,product.productName);
      const productText = createAmountLabel(product.productNo, product.productName, product.amount);

      productWrapper.appendChild(productImageWrapper);
      productImageWrapper.appendChild(productImageDiv);
      productImageWrapper.appendChild(productText);

      return productWrapper;})
    .forEach(productNode => productSliderWrapper.appendChild(productNode));
})
function addCart(productNo) {
    const token = document.getElementById("_csrf").content;
    const header = document.getElementById("_csrf_header").content;
    $.ajax({
        url: `/carts/modify-quantity?productNo=${productNo}&productQuantity=1`,
        method:"PUT",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header,token);
        },
        success: function(data){
            alert(data);
        },
        error: function(data){
            alert(data);
        }
    })
}
