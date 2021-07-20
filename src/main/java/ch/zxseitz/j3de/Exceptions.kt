package ch.zxseitz.j3de

import ch.zxseitz.j3de.graphics.core.Program
import ch.zxseitz.j3de.graphics.core.Shader
import ch.zxseitz.j3de.graphics.mesh.VertexBuffer

open class J3deException(message: String): Exception(message)

open class ProgramException(message: String, val program: Program?): J3deException(message)

open class AttribException(message: String, program: Program?, val name: String?): ProgramException(message, program)

open class BufferException(message: String, val buffer: VertexBuffer?): J3deException(message)

open class ResourceNotFoundException(path: String): J3deException("Resource not found: $path")

open class ShaderException(message: String, val shader: Shader?): J3deException(message)

open class UniformException(message: String, program: Program?, val name: String?): ProgramException(message, program)

open class WindowException(message: String, val window: Long): J3deException(message)
