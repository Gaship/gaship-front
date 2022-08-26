const pageHelper = {
    pagingSize: 10,
    page: 0,
    lastPage: 0,
    pageContainer:"",
    lastPageContainer:"",
    pageItems: [],
    plusPage: (itemList) => {
        pageHelper.page += 1;
        pageHelper.reloadPage();
        pageHelper.pageItems = itemList;
    },
    subPage: (itemList) => {
        pageHelper.page -= 1;
        pageHelper.reloadPage();
        pageHelper.pageItems = itemList;
    },
    reloadPage: () => {
        this.pageContainer.innerHtml = pageHelper.page + 1;
    },
    initPage: (itemList) => {
        // pageHelper.lastPageContainer.innerHtml = itemList.totalPages;
        pageHelper.pageItems = itemList.content;
    }
};

export default pageHelper;