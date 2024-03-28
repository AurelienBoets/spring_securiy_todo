// Transforme l'enum fournit par l'api en String

export function stateEnum(value) {
  let state;
  switch (value) {
    case "IN_PROGRESS":
      state = "En cours";
      break;
    case "COMPLETED":
      state = "Terminé";
      break;
    default:
      state = "unknown";
      break;
  }
  return state;
}
