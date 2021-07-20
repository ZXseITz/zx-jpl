package ch.zxseitz.j3de.graphics.core;

data class ShaderAttribute(val name: String, val size: Int) {
    companion object {
        val POS = ShaderAttribute("pos", 3);
        val NORMAL = ShaderAttribute("normal", 3);
        val COLOR = ShaderAttribute("color", 4);
        val UV = ShaderAttribute("uv", 2);
    }

    override fun toString(): String {
        return String.format("($name, $size");
    }
}
