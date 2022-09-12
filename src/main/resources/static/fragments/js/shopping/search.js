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
    searchWrapper.classList.add("invisible");
    searchWrapper.classList.remove("visible");
    searchWrapper.removeChild(searchResultBox);
  }

  if (searchResult.length > 0) {
    const searchResults = document.querySelector(".search-result");
    if(searchResults){
      searchWrapper.removeChild(searchResults);
    }

    const searchResultBox = document.createElement("div");
    searchResultBox.classList.add("search-result");
    searchResult.forEach(result => {
      const divTag = document.createElement("div");
      const pTag = document.createElement("p");
      const searchResultLink = document.createElement("a");
      searchResultLink.classList.add('text-dark');

      searchResultLink.href = `https://gaship.shop/products/${result.id}`;
      searchResultLink.innerHTML = result.name + " (" + result.code + ")";
      pTag.append(searchResultLink);
      divTag.classList.add("hero__search__form", "p-2", "w-100", "h-100", "text-dark", "bg-white");
      divTag.append(pTag)
      searchResultBox.append(divTag);
      searchWrapper.append(searchResultBox);
      searchWrapper.classList.add("visible");
      searchWrapper.classList.remove("invisible");
      searchWrapper.style.zIndex = 100;
    });
  }
}

const searchEvent = () => document
  .querySelector(".search-box")
  .addEventListener("change", e => debounce(doSearch, 500)(e));

const searchKeywordEvent = () => document.querySelector(".search-form").addEventListener('submit', (e) => {
  e.preventDefault();
  const keyword = document.querySelector(".search-box").value;
  location.replace(`/products?page=0&size=12&keyword=${keyword}`);
});

window.addEventListener('load', () => searchKeywordEvent());
window.addEventListener('load', () => searchEvent());
