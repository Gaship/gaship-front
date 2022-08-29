async function getMemberCoupons() {
    const request = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    };

    return await fetch("/rest/order/coupons", request)
        .then(response => {
            return response.json();
        });
}

function setMemberCouponsData(memberCouponList) {
    // console.log(memberCouponList);
}

const loadMemberCoupons = () => {
    getMemberCoupons()
        .then(setMemberCouponsData)
}

export {loadMemberCoupons}