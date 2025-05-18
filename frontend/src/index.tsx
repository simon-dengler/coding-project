import CssBaseline from '@mui/material/CssBaseline';
import {ThemeProvider} from '@mui/material/styles';
import App from './home/app';
import theme from './theme';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Layout from "./layout/layout";
import Form from "./form/form";
import Game from "./game/game";
import Result from "./result/result";
import UsernameCheck from "./account/usernameCheck";
import {createRoot} from "react-dom/client";

const rootElement = document.querySelector('#root');

if (rootElement) {
    createRoot(rootElement).render(
        <Router>
            <ThemeProvider theme={theme}>
                <CssBaseline enableColorScheme/>
                <Routes>
                    <Route element={<Layout />}>
                        <Route path="/" element={<App/>}/>
                        <Route path="/form" element={<Form/>}/>
                        <Route path="/form/:formId" element={<Form/>}/>
                        <Route path="/game/:formId" element={<Game/>}/>
                        <Route path="/result/:formId" element={<Result/>}/>
                        <Route path="/check" element={<UsernameCheck/>}/>
                    </Route>
                </Routes>
            </ThemeProvider>
        </Router>
    );
}
