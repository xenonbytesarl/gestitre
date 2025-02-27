import i18n from "@/i18n.tsx";

export const API_BASE_URL = import.meta.env.VITE_API_BACKEND_URL;

export const API_JSON_HEADER = {
    'Content-Type': 'application/json',
    'Accept-Language': i18n.language
}

export const API_JSON_WITH_TIME_ZONE_HEADER = {
    'Content-Type': 'application/json',
    'Accept-Language': i18n.language,
    'X-Gestitre-Timezone': Intl.DateTimeFormat().resolvedOptions().timeZone
}

export const API_FORM_DATA_HEADER = {
    'Content-Type': 'multipart/form-data',
    'Accept-Language': i18n.language
}

export const UNKNOWN_ERROR = 'Unknown error - Contact your administrator';

export type ToastType = "danger" | "info" | "success";

export const DEFAULT_COMPANY_LOGO_IMAGE = '/images/company_logo.png';
export const DEFAULT_COMPANY_STAMP_IMAGE = '/images/company_stamp.png';

export const DEBOUNCE_TIMEOUT = 500;