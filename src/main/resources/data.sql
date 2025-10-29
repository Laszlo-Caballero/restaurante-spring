INSERT INTO public.usuarios (nombre, username, password, role)
VALUES ('admin', 'admin', '$2a$10$16XMDwrwnCfjqiHt8pnWe.C3K.deF0mHA2BTf40ht6VlTZGt2Negu', 'ADMIN')
ON CONFLICT (username) DO NOTHING;