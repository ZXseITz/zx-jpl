package ch.zxseitz.j3de.graphics.programs;

import ch.zxseitz.j3de.graphics.programs.uniforms.IUniform;

import java.util.*;

import static org.lwjgl.opengl.GL45.*;

public class Program {
    private int id;
    private List<Shader> shaders;
    private List<ShaderAttribute> attributes;
    private List<IUniform> uniforms;

    public Program (Shader[] shaders, ShaderAttribute[] attributes) {
        try {
            this.shaders = Collections.unmodifiableList(List.of(shaders));
            this.attributes = Collections.unmodifiableList(List.of(attributes));
            this.uniforms = new ArrayList<>();
            this.id = glCreateProgram();
            // attach shader
            for (var shader : shaders) {
                if (shader.isDeleted())
                    throw new RuntimeException("Cannot attach deleted shader " + shader);
                glAttachShader(id, shader.getId());
            }
            // link program
            glLinkProgram(id);
            if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
                throw new RuntimeException("Error linking program\n" +
                        glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public int getId() {
        return id;
    }

    public List<Shader> getShaders() {
        return shaders;
    }

    public List<ShaderAttribute> getAttributes() {
        return attributes;
    }

    public List<IUniform> getUniforms() {
        return uniforms;
    }

    public void use() {
        glUseProgram(this.id);
        for (var uniform : uniforms) {
            uniform.write(getUniformLocation(uniform.getName()));
        }
    }

    public int getUniformLocation(String name) {
        var location = glGetUniformLocation(this.id, name);
        if (location < 0)
            throw new RuntimeException(String.format("AbstractUniform %s has no location in program %d", name, id));
        return location;
    }

    public int getAttribLocation(String name) {
        var location = glGetAttribLocation(this.id, name);
        if (location < 0)
            throw new RuntimeException(String.format("Attribute %s has no location in program %d", name, id));
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Program){
            return id == ((Program) obj).id;
        }
        return false;
    }
}
