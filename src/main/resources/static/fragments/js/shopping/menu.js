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
}

window.addEventListener('load', () => shoppingMallCategories());
