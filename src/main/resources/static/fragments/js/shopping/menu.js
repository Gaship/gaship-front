let flattedCategories = [];

const flatMapCategory = (categories) => categories.forEach(category => {
  const lowerCategoriesLen = category.lowerCategories.length;
  if (lowerCategoriesLen == 0) {
    flattedCategories = [...flattedCategories, category];
  } else {
    flattedCategories = [...flattedCategories, category];
    flatMapCategory(category.lowerCategories)
  }
});

const productsPageCategories = () => {
  const sidebar = document.querySelector(".sidebar__item-cartegory");

  flattedCategories.filter(category => category.level === 1)
    .forEach((category, idx) => {
      const ul = document.createElement("ul");
      const li = document.createElement("li");
      const label = document.createElement("label");
      const inputElement = document.createElement("input");
      const spanElement = document.createElement("span");

      spanElement.innerHTML = category.name;
      inputElement.value = category.name;
      inputElement.name = "category";
      inputElement.type = "radio";
      inputElement.classList.add("mr-3");
      inputElement.checked = true;

      const attributeName = "category-id";
      inputElement.setAttribute(attributeName, category.no);
      inputElement.classList.add(attributeName);

      inputElement.addEventListener(('click'), () => {
        const searchUrlTag = document.getElementById("product-filter");
        const urls = searchUrlTag.href.split("&");

        urls[1] = "category=" + category.no;
        searchUrlTag.href = urls.join("&");
      })

      li.appendChild(label);
      ul.appendChild(li);
      label.appendChild(inputElement);
      label.appendChild(spanElement);

      sidebar.appendChild(ul);
    });
}

const shoppingMallCategories = async () => {
  const res = await fetch("/api/categories");
  const categories = await res.json();

  flatMapCategory(categories);

  const categoriesNode = document.querySelector(".categories");


  flattedCategories.forEach(loadedCategory => {
    // href 넣어야함
    const category = document.createElement("li");
    const categoryAtag = document.createElement("a");
    categoryAtag.href = loadedCategory.no;
    categoryAtag.innerHTML = loadedCategory.name;
    if(loadedCategory.level === 1){
      categoryAtag.classList.add("categories__item__level_1")
    }
    if(loadedCategory.level === 2){
      categoryAtag.classList.add("categories__item__level_2")
    }
    if(loadedCategory.level === 3){
      categoryAtag.classList.add("categories__item__level_3")
    }
    categoryAtag.innerHTML = loadedCategory.name;
    category.appendChild(categoryAtag);
    categoriesNode.appendChild(category);
  });

  productsPageCategories();
}

window.addEventListener('load', () => shoppingMallCategories());

