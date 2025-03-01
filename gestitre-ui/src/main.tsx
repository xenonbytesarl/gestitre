import {createRoot} from 'react-dom/client';
import {BrowserRouter as Router} from "react-router-dom";
import {Provider} from "react-redux";
import AppRoute from "@/core/AppRoute.tsx";
import store from "@/core/Store.ts";
import './index.css';
import './core/i18n.tsx';
import Interceptors from "@/core/Interceptors.ts";

createRoot(document.getElementById('root')!).render(
    <Provider store={store}>
        <Router>
            <AppRoute/>
        </Router>
    </Provider>
);

Interceptors(store);


