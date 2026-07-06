import React, { useCallback, useEffect } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import { AuthApi } from "./apis/apps/AuthApi";
import { useAppDispatch } from "./apps/hooks";
import ErrorScreen from "./components/common/ErrorScreen";
import { setUser } from "./features/user/userSlice";
import AdminLayout from "./layouts/AdminLayout";
import { routeApplications } from "./Routes";
import SignInAdminScreen from "./screens/admin/login/SignInAdminScreen";
import { isAuthenticated, logout } from "./utils/authUtil";
import { RouteUrls } from "./utils/urlUtils";

const App: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  // useEffect(() => {
  //   connectWebSocket(
  //     (msg) => console.log("Received:", msg),
  //     () => console.log("Connected"),
  //     (err) => console.error("Error:", err)
  //   );

  //   return () => disconnectWebSocket();
  // }, []);

  const fetchUserData = useCallback(async () => {
    if (isAuthenticated()) {
      try {
        const userData = await AuthApi.getCurrentUser();
        dispatch(setUser(userData));
      } catch (error) {
        // Xử lý lỗi nếu cần thiết
      }
    } else {
      logout();
      navigate("/login");
    }
  }, [dispatch, navigate]);

  useEffect(() => {
    fetchUserData();
  }, [fetchUserData]);

  return (
    <>
      <Routes>
        <Route path="/" element={<AdminLayout />}>
          {routeApplications}
        </Route>
        <Route path={RouteUrls.login} element={<SignInAdminScreen />} />
        <Route path={RouteUrls.register} element={<>Đăng nhập</>} />
        <Route
          path={RouteUrls.unauthorized}
          element={
            <ErrorScreen
              status="403"
              title="403"
              subTitle="Sorry, you are not authorized to access this page."
              redirectTo="/"
            />
          }
        />
        <Route
          path={RouteUrls.error}
          element={
            <ErrorScreen
              status="500"
              title="500"
              subTitle="Sorry, something went wrong."
              redirectTo="/"
            />
          }
        />
        <Route
          path="*"
          element={
            <ErrorScreen
              status="404"
              title="404"
              subTitle="Sorry, the page you visited does not exist."
              redirectTo="/"
            />
          }
        />
      </Routes>
    </>
  );
};

export default App;
