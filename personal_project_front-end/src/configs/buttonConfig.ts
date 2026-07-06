export interface ButtonConfig {
  type: "primary" | "dashed" | "link" | "text" | "default";
  label: string;
  onClick: () => void;
  disabled?: boolean;
  danger?: boolean;
}
