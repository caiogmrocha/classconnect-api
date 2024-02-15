/**
 * POST /api/perfis/login permitAll
 * POST /api/perfis/cadastrar permitAll
 * PUT /api/perfis/{idPerfil} authenticated
 * DELETE /api/perfis/{idPerfil} authenticated
 * 
 * GET /api/salas authenticated
 * POST /api/salas ROLE_PROFESSOR
 * GET /api/salas/{idSala} authenticated
 * PUT /api/salas/{idSala} ROLE_PROFESSOR
 * DELETE /api/salas/{idSala} ROLE_PROFESSOR
 * GET /api/salas/{idSala}/alunos authenticated
 * 
 * GET /api/salas/{idSala}/posts authenticated
 * POST /api/salas/{idSala}/posts ROLE_PROFESSOR
 * PUT /api/salas/{idSala}/posts/{idPost} ROLE_PROFESSOR
 * DELETE /api/salas/{idSala}/posts/{idPost} ROLE_PROFESSOR
 * 
 * GET /api/salas/{idSala}/posts/{idPost}/resposta/{idAluno} authenticated
 * POST /api/salas/{idSala}/posts/{idPost}/resposta ROLE_ALUNO
 * 
 * GET /api/salas/{idSala}/posts/{idPost}/comentarios authenticated
 * POST /api/salas/{idSala}/posts/{idPost}/comentarios authenticated
 * DELETE /api/salas/{idSala}/posts/{idPost}/comentarios/{idComentario}
 * authenticated
 * 
 * GET /api/salas/{idSala}/posts/{idPost}/curtidas/quantidade authenticated
 * PATCH /api/salas/{idSala}/posts/{idPost}/curtidas/curtir authenticated
 * 
 * POST /api/salas/{iSala}/convidar/{idAluno} ROLE_PROFESSOR
 * PATCH /api/salas/{idSala}/aceitar/{idAluno} ROLE_PROFESSOR
 * 
 * POST /api/salas/{iSala}/matriculas/solicitar ROLE_ALUNO
 * PATCH /api/salas/{idSala}/matriculas/aceitar ROLE_ALUNO
 * DELETE /api/salas/{idSala}/matriculas/recusar ROLE_ALUNO
 * DELETE /api/salas/{idSala}/matriculas/desfazer ROLE_ALUNO
 * 
 * POST /api/salas/{iSala}/matriculas/solicitar/{idAluno} ROLE_PROFESSOR
 * PATCH /api/salas/{idSala}/matriculas/aceitar/{idAluno} ROLE_PROFESSOR
 * DELETE /api/salas/{idSala}/matriculas/recusar/{idAluno} ROLE_PROFESSOR
 * DELETE /api/salas/{idSala}/matriculas/desfazer/{idAluno} ROLE_PROFESSOR
 */