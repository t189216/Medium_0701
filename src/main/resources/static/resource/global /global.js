/* 유틸, 토스틀 */
toastr.options = {
    closeButton: true,
    debug: false,
    newestOnTop: true,
    progressBar: true,
    positionClass: "toast-top-right",
    preventDuplicates: false,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "5000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};

function parseMsg(msg) {
    const [pureMsg, ttl] = msg.split(";ttl=");

    if ( ttl === undefined ) {
        return [pureMsg, true];
    };

    const currentJsUnixTimestamp = new Date().getTime();

    if (ttl && parseInt(ttl) < currentJsUnixTimestamp) {
        return [pureMsg, false];
    }

    return [pureMsg, true];
}

function toastMsg(isNotice, msg) {
    if (isNotice) toastNotice(msg);
    else toastWarning(msg);
}

function toastNotice(msg) {
    const [pureMsg, needToShow] = parseMsg(msg);

    if (needToShow) {
        toastr["success"](pureMsg, "알림");
    }
}

function toastWarning(msg) {
    const [pureMsg, needToShow] = parseMsg(msg);

    if (needToShow) {
        toastr["warning"](pureMsg, "경고");
    }
}

/* 유틸, URL */
function getUrlParams(urlString) {
    // 평문 쿼리 문자열을 추출합니다.
    const url = new URL(urlString);
    // URL의 쿼리 문자열 이후로 모든 키와 값을 디코딩합니다.
    const queryParams = new URLSearchParams(url.search);
    // 객체를 만들어 줍니다.
    const params = {};

    for (let [key, value] of queryParams.entries()) {
        // 객체에 키와 값을 추가합니다.
        params[key] = value;
    }

    return params;
}