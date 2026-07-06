import { useAppSelector } from "@/apps/hooks";
import { RoleEnum } from "@/enums/RoleEnum";
import { selectCurrentUser } from "@/features/user/userSlice";
import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";

interface RequireRoleProps {
  children: React.ReactNode;
  allowedRoles: RoleEnum[];
  redirectTo?: string;
}

const RequireRole: React.FC<RequireRoleProps> = ({
  children,
  allowedRoles,
  redirectTo = "/unauthorized",
}) => {
  const user = useAppSelector(selectCurrentUser);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [hasPermission, setHasPermission] = useState<boolean | null>(null);

  useEffect(() => {
    if (user !== null) {
      const permission = user?.roles?.some((role) =>
        allowedRoles.includes(role)
      );
      setHasPermission(permission ?? false);
      setIsLoading(false);
    }
  }, [user, allowedRoles]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!hasPermission) {
    return <Navigate to={redirectTo} replace />;
  }

  return <>{children}</>;
};

export default RequireRole;
