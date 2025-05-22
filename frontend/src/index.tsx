import CssBaseline from '@mui/material/CssBaseline';
import {ThemeProvider} from '@mui/material/styles';
import App from './home/app';
import theme from './theme';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Layout from "./layout/layout";
import Form from "./form/form";
import Game from "./game/game";
import Result from "./result/result";
import Check from "./account/check";
import {createRoot} from "react-dom/client";
import Account from "./account/account";
import RouteProtection from "./route-protection/routeProtection";
import Login from './account/login';
import Register from "./account/register";

const rootElement = document.querySelector('#root');

if (rootElement) {
    createRoot(rootElement).render(
        <Router>
            <ThemeProvider theme={theme}>
                <CssBaseline enableColorScheme/>
                <Routes>
                    <Route element={<Layout />}>
                        <Route path="/" element={<App/>}/>
                        <Route path="/check" element={<Check/>}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/login/:username" element={<Login/>}/>
                        <Route path="/register" element={<Register />}/>
                        <Route path="/register/:username" element={<Register/>}/>
                        <Route element={<RouteProtection />}>
                            <Route path="/form" element={<Form/>}/>
                            <Route path="/form/:formId" element={<Form/>}/>
                            <Route path="/game/:formId" element={<Game/>}/>
                            <Route path="/result/:jackpotId" element={<Result/>}/>
                            <Route path="/account" element={<Account/>}/>
                        </Route>
                    </Route>
                </Routes>
            </ThemeProvider>
        </Router>
    );
}
