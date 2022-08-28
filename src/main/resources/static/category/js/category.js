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
        appendCategoryModifyButton(category, loadedCategory.no)
        appendCategoryRemoveButton(category, loadedCategory.no)

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

const appendCategoryModifyButton = (element, categoryNo) => {
    const modifyBtn = document.createElement("button");
    modifyBtn.innerHTML += "수정"
    modifyBtn.className = "btn btn-secondary ml-2 px-1 py-0";
    modifyBtn.addEventListener("click", function () {moveModifyForm(categoryNo)})
    element.appendChild(modifyBtn);
}

const appendCategoryRemoveButton = (element, categoryNo) => {
    const removeBtn = document.createElement("button");
    removeBtn.innerHTML += "삭제"
    removeBtn.className = "btn btn-danger ml-2 px-1 py-0";
    removeBtn.addEventListener("click", function () {removeCategory(categoryNo)})
    element.appendChild(removeBtn);
}

const moveAddForm = (upperCategoryNo) => {
    location.href = "/categories/add?upperCategoryNo=" + upperCategoryNo;
}

const moveModifyForm = (categoryNo) => {
    location.href = "/categories/" + categoryNo + "/modify";
}

const removeCategory = (categoryNo) => {
    if (confirm("삭제하시겠습니까?")) {
        location.href = "/categories/remove?categoryNo=" + categoryNo;
    }
}

window.addEventListener('load', () => shoppingMallCategories());

