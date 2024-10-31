import { createRoot } from 'react-dom/client';
import {BrowserRouter as Router} from "react-router-dom";
import {Provider} from "react-redux";
import AppRoute from "@/AppRoute.tsx";
import {store} from "@/Store.ts";
import './index.css';
import './i18n';


createRoot(document.getElementById('root')!).render(
    <Provider store={store}>
        <Router>
            <AppRoute/>
        </Router>
    </Provider>
)
