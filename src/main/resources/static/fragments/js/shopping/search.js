const debounce = (func, delay) => {
  let timer;

  return (event) => {
    if (timer) {
      clearTimeout(timer);
    }

    timer = setTimeout(func(event), delay);
  }
}


const doSearch = async (e) => {
  const keyoword = e.target.value;
  const response = await fetch(`/api/search?keyword=${keyoword}`);
  const searchResult = await response.json();

  const searchWrapper = document.querySelector(".search-wrapper");
  if (!searchResult.length) {
    const searchResultBox = document.querySelector(".search-result");
    searchWrapper.removeChild(searchResultBox);
  }

  if (searchResult.length > 0) {
    const searchResultBox = document.createElement("div");
    searchResultBox.classList.add("search-result");
    searchResult.forEach(result => {
      const divTag = document.createElement("div");
      const pTag = document.createElement("p");
      const searchResultLink = document.createElement("a");

      searchResultLink.href = `https://gaship.shop/products/product?name=${result.id}`;
      searchResultLink.innerHTML = result.name + " (" + result.code + ")";
      pTag.append(searchResultLink);
      divTag.classList.add("hero__search__form", "p-2", "w-100", "h-100", "text-dark", "bg-white");
      divTag.append(pTag)
      searchResultBox.append(divTag);
      searchWrapper.append(searchResultBox);
    });
  }
}

const searchEvent = () => document
  .querySelector(".search-box")
  .addEventListener("change", debounce(doSearch, 500));

window.addEventListener('load', () => searchEvent());
