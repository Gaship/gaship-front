const getDeliveryLocation = async () => {
  const trackingNo = document.getElementById("tracking-no").innerText;

  const response = await fetch(`/eggplant/delivery/location-status?trackingNo=${trackingNo}`);
  const result = await response.json();

  const deliveryInfo = document.getElementById("delivery-info");
  const totalTable = document.getElementById("order-product-body");

  deliveryInfo.innerHTML = "";
  totalTable.innerHTML = "";

  if (result.size > 0 && result[0].completionTime !== null) {
    deliveryInfo.append(`도착 시간 : ${result[0].completionTime}`);
    deliveryInfo.append(`배송 상태 : ${result[0].status}`)
  }

  result.forEach(deliveryLocationResult => {
    const trTag = document.createElement("tr");
    const arrivalTimeTag = document.createElement("td");
    const arrivalLocationTag = document.createElement("td");
    arrivalTimeTag.innerHTML = deliveryLocationResult.arrivalTime;
    arrivalLocationTag.innerHTML = deliveryLocationResult.middleLocation;

    trTag.append(arrivalTimeTag, arrivalLocationTag);

    totalTable.append(trTag);
  })
}

document.getElementById("delivery-location-btn").addEventListener("click", getDeliveryLocation);
