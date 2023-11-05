package ru.serioussem.wander.game.constants;

public enum TypeCell {
    GREEN("green"),
    BLUE("blue"),
    YELLOW("yellow"),
    RED("red"),
    WHITE("white");

    private String type;

    TypeCell(String type) {
        this.type = type;
    }

    static public TypeCell getByType(String type) throws IllegalArgumentException {
        for (TypeCell cell : values()) {
            if (type.equals(cell.type)) {
                return cell;
            }
        }
        throw new IllegalArgumentException("Неизвестный TypeCell: " + type);
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TypeCell{" +
                "type='" + type + '\'' +
                '}';
    }
}
