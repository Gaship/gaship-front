const changeSalesStatus = async (element) => {
    token = document.querySelector('meta[name="_csrf"]').content;
    let params = element.value.split("=")

    const request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN":token
        },
        body: JSON.stringify({
            "productNo":params[0],
            "statusCodeName":params[1]
        })
    };

    await fetch("/api/products/sales-status", request);
}