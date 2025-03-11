

export const changeNullToEmptyString = (custom: object, defaultCustom: object) => {
    // @ts-ignore
    const result = JSON.parse(JSON.stringify(custom), (key, value) => value === null ? defaultCustom[key] : value);
    console.log(result)
    return result;
}
