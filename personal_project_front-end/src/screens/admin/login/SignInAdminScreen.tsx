import { AuthApi } from "@/apis/apps/AuthApi";
import { LoginRequest } from "@/models/request/LoginRequest";
import { saveToken } from "@/utils/authUtil";
import { notify } from "@/utils/notification";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export const SignInAdminScreen: React.FC = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    if (!username || !password) {
      setError("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
      return;
    }
    try {
      const body: LoginRequest = { username, password };
      const response = await AuthApi.login(body);
      saveToken(response);
      notify.success("Đăng nhập thành công!");
      navigate("/", { replace: true });
    } catch (err) {
      notify.error("Đăng nhập thất bại!");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-tr from-gray-900 to-indigo-800 p-4">
      <div className="max-w-md w-full bg-white/10 backdrop-blur-md rounded-2xl shadow-lg p-8">
        <h2 className="text-3xl font-bold text-white text-center mb-6">
          Welcome Room Management
        </h2>
        {error && (
          <div className="bg-red-500 text-white text-center py-2 rounded mb-4">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="text-white block mb-1 font-semibold">
              Username
            </label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full px-4 py-2 rounded-lg bg-white/20 text-white placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-400"
              placeholder="Enter your username"
            />
          </div>
          <div>
            <label className="text-white block mb-1 font-semibold">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2 rounded-lg bg-white/20 text-white placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-400"
              placeholder="Enter your password"
            />
          </div>
          <button
            type="submit"
            className="w-full flex items-center justify-center py-2 bg-indigo-500 hover:bg-indigo-600 text-white font-semibold rounded-lg transition"
          >
            Sign In
          </button>
        </form>
        <p className="text-center text-sm text-gray-300 mt-6">
          © 2025 YourCompany
        </p>
      </div>
    </div>
  );
};

export default SignInAdminScreen;
