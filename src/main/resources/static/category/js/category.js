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

    const table = document.querySelector(".categories");

    flattedCategories.forEach(loadedCategory => {
        const tr = document.createElement("tr");
        const category = document.createElement("td");
        category.innerHTML = "&nbsp".repeat(loadedCategory.level * 4) + loadedCategory.name;

        if (loadedCategory.level < 3) {
            appendCategoryAddButton(category, loadedCategory.no)
        }

        const modifyBtn = document.createElement("button");
        modifyBtn.innerHTML += "수정"
        modifyBtn.className = "btn btn-secondary ml-2 px-1 py-0";
        category.appendChild(modifyBtn);

        const removeBtn = document.createElement("button");
        removeBtn.innerHTML += "삭제"
        removeBtn.className = "btn btn-danger ml-2 px-1 py-0";
        category.appendChild(removeBtn);

        tr.appendChild(category);
        table.appendChild(tr);
    });

    const tr = document.createElement("tr");
    const td = document.createElement("td");
    appendCategoryAddButton(td, null);
    tr.appendChild(td)
    table.appendChild(tr);
}

const appendCategoryAddButton = (element, upperCategoryNo) => {
    const addBtn = document.createElement("button");
    addBtn.innerHTML += "추가"
    addBtn.className = "btn btn-primary ml-2 px-1 py-0";
    addBtn.addEventListener("click", function () {moveAddForm(upperCategoryNo)})
    element.appendChild(addBtn);
}

const moveAddForm = (upperCategoryNo) => {
    location.href = "/categories/add?upperCategoryNo=" + upperCategoryNo;
}

window.addEventListener('load', () => shoppingMallCategories());

