DECLARE
  V_ID_USUARIO        TB_USUARIO.ID_USUARIO%TYPE;
  V_ID_SISTEMA        TB_SISTEMA.ID_SISTEMA%TYPE   := ACCESSCONTROL.SEQ_SISTEMA.NEXTVAL;
  V_DS_SISTEMA        TB_SISTEMA.NOME%TYPE         := 'DXC - Relatorios'; --Nome do Novo Sistema (nome que vai aparecer na tela do AccessControl)
  V_URL_BASE          TB_SISTEMA.URL_BASE%TYPE     := '/CMS-ISSUER-DXC-RELATORIOS'; --Context da Aplicacao
  V_KEY_SISTEMA       TB_SISTEMA.SYSTEM_KEY%TYPE   := 'CMSISSUERDXCRELATORIOS'; --Chave UNICA do sistema (usado para autenticação no Access Control pelo Backend)
  V_ID_OPER_ADMIN     TB_OPERACAO.ID_OPERACAO%TYPE := ACCESSCONTROL.SEQ_OPERACAO.NEXTVAL;
  V_DS_OPER_ADMIN     TB_OPERACAO.DESCRICAO%TYPE   := 'Rel. Eventos 1386'; --Nome da Operação
  V_KEY_OPER_ADMIN    TB_OPERACAO.CHAVE%TYPE       := 'dxcrel.eventos.1386';
  V_KEY_PERF_CONSULTA TB_PERFIL.CHAVE%TYPE         := 'dxcrel.eventos.1386';
  V_ID_PERF_ADMIN     TB_PERFIL.ID_PERFIL%TYPE     := ACCESSCONTROL.SEQ_PERFIL.NEXTVAL;
  V_DS_PERF_ADMIN     TB_PERFIL.DESCRICAO%TYPE     := 'DXC - Relatorios-Rel 1386'; --Nome do Perfil
  V_KEY_PERF_ADMIN    TB_PERFIL.CHAVE%TYPE         := ACCESSCONTROL.PKG_ACCESSCONTROL_SOAP.fnGenerateKey;
BEGIN
  -- Obtencao de um usuario qualquer para cadastro do sistema
  SELECT MAX(ID_USUARIO)
    INTO V_ID_USUARIO
    FROM ACCESSCONTROL.TB_USUARIO;
  -- Criacao do sistema
  INSERT INTO ACCESSCONTROL.TB_SISTEMA  (ID_SISTEMA,         NOME,               URL_BASE,   ID_USUARIO,   ATIVO, SYSTEM_KEY)
                                 VALUES (V_ID_SISTEMA,       V_DS_SISTEMA,       V_URL_BASE, V_ID_USUARIO, 1,     V_KEY_SISTEMA);
  -- Criacao da operacao (admin)
  INSERT INTO ACCESSCONTROL.TB_OPERACAO (ID_OPERACAO,        DESCRICAO,          CHAVE,            ID_SISTEMA)
                                 VALUES (V_ID_OPER_ADMIN,    V_DS_OPER_ADMIN,    V_KEY_OPER_ADMIN, V_ID_SISTEMA);
  -- Criacao do Perfil (admin)
  INSERT INTO ACCESSCONTROL.TB_PERFIL   (ID_PERFIL,          DESCRICAO,          ATIVO,      CHAVE)
                                 VALUES (V_ID_PERF_ADMIN,    V_DS_PERF_ADMIN,    1,          V_KEY_PERF_ADMIN);
  -- Criacao do Perfil x Operacao (admin)
  INSERT INTO ACCESSCONTROL.TB_PERFIL_OPERACAO (ID_PERFIL, ID_OPERACAO) VALUES (V_ID_PERF_ADMIN, V_ID_OPER_ADMIN);
  COMMIT;
END;
/