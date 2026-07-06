import { LoadingProvider } from "@/contexts/LoadingContext";
import { NotificationProvider } from "@/contexts/NotificationContext";
import { StyleProvider } from "@ant-design/cssinjs";
import { ConfigProvider } from "antd";
import React from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import { store } from "./apps/store";
import "./styles/index.css";
import "./styles/tailwind.css";
import GlobalLoading from "./components/common/Loading";

const container = document.getElementById("root");

if (!container) throw new Error("Could not find root element with id 'root'");

const root = createRoot(container);

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter basename={import.meta.env.BASE_URL}>
        <ConfigProvider
          theme={{
            components: {
              Notification: {
                zIndexPopup: 10000,
              },
            },
          }}
        >
          <StyleProvider hashPriority="high">
            <LoadingProvider>
              <NotificationProvider>
                <App />
                <GlobalLoading />
              </NotificationProvider>
            </LoadingProvider>
          </StyleProvider>
        </ConfigProvider>
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
);
