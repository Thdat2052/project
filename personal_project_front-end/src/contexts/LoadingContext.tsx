import React, { useContext, useState } from "react";

interface LoadingContextType {
  isLoading: boolean;
  startLoading: () => void;
  stopLoading: () => void;
}

const LoadingContext = React.createContext<LoadingContextType | undefined>(
  undefined
);

export function useLoading(): LoadingContextType {
  const context = useContext(LoadingContext);
  if (!context) {
    throw new Error("useLoading must be used within a LoadingProvider");
  }
  return context;
}

export function LoadingProvider({ children }: { children: React.ReactNode }) {
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const startLoading = async () => {
    setIsLoading(true);
  };

  const stopLoading = async () => {
    setIsLoading(false);
  };

  const contextValue: LoadingContextType = {
    isLoading,
    startLoading,
    stopLoading,
  };

  return (
    <LoadingContext.Provider value={contextValue}>
      {children}
    </LoadingContext.Provider>
  );
}
