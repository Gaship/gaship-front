function MemberCoupon(memberCoupon) {
    this.couponNo = memberCoupon.couponGenerationIssueNo;
    this.couponName = memberCoupon.couponName;
    this.discountAmount = memberCoupon.discountAmount;
    this.discountRate = memberCoupon.discountRate;
    this.expirationDatetime = memberCoupon.expirationDatetime;
    this.issueDatetime = memberCoupon.issueDatetime;
    this.used = false;
}

const memberCoupons = {
    memberCouponList: [],
    getDiscountAmount: (productAmount, couponNo) => {
        const coupon = memberCoupons.memberCouponList.filter(memberCouponData =>
            memberCouponData.couponNo == couponNo
        )
        if (coupon[0].discountAmount === null) {
            return productAmount * (coupon[0].discountRate / 100);
        } else {
            return coupon[0].discountAmount;
        }
    },
    selectCoupon: (couponNo) => {
        const selectedCoupon = memberCoupons.memberCouponList.filter(memberCouponData =>
            memberCouponData.couponNo == couponNo);
        selectedCoupon[0].used = true;

        const selectedCouponIndex = memberCoupons.memberCouponList.indexOf(selectedCoupon[0]);
        memberCoupons.memberCouponList[selectedCouponIndex] = selectedCoupon[0];
    },
    unselectCoupon: (couponNo) => {
        const unSelectedCoupon = memberCoupons.memberCouponList.filter(memberCouponData =>
            memberCouponData.couponNo == couponNo);
        unSelectedCoupon[0].used = false;

        const unSelectedCouponIndex = memberCoupons.memberCouponList.indexOf(unSelectedCoupon[0]);
        memberCoupons.memberCouponList[unSelectedCouponIndex] = unSelectedCoupon[0];
    },
    getUnselectedCoupon: () => {
        return memberCoupons.memberCouponList;
    },
    init: (memberCouponDataList) => {
        memberCoupons.memberCouponList = memberCouponDataList.map(memberCouponData => {
            return new MemberCoupon(memberCouponData);
        })
    },
    checkSelectedCoupon: (couponNo) => {
        const checkTarget = memberCoupons.memberCouponList
            .filter(coupon => coupon.couponNo == couponNo);
        return checkTarget[0].used === true;
    }
}

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
    memberCoupons.init(memberCouponList);
}

const loadMemberCoupons = () => {
    getMemberCoupons()
        .then(setMemberCouponsData)
}

export {loadMemberCoupons, memberCoupons}